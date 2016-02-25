//
//  AddViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "AddViewController.h"
#import "HomeViewController.h"

@interface AddViewController ()

@end

@implementation AddViewController
@synthesize typeArray;

#pragma mark -
#pragma mark System

//checks for internet
-(BOOL)Internet{
    //Make address string a url
    NSString *address = @"https://parse.com/";
    NSURL *url = [NSURL URLWithString:address];
    //create request
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    [request setHTTPMethod:@"HEAD"];
    //send request
    NSHTTPURLResponse *results;
    [NSURLConnection sendSynchronousRequest:request returningResponse:&results error:NULL];
    //get results
    if([results statusCode] == 200){
        return true;
    } else {
        return false;
    }
}
//Pattern validation
-(BOOL)isPhoneNumber:(NSString *)num {
    if(num.length == 10)
    {
        NSCharacterSet *numbersOnly = [NSCharacterSet characterSetWithCharactersInString:@"0123456789"];
        NSCharacterSet *characterSetFromTextField = [NSCharacterSet characterSetWithCharactersInString:num];
        BOOL stringIsValid = [numbersOnly isSupersetOfSet:characterSetFromTextField];
        return stringIsValid;
    } else {
        return NO;
    }
}
//String Validation
-(BOOL)isString:(NSString *)string{
    if (string.length > 0) {
        NSCharacterSet *letters = [NSCharacterSet characterSetWithCharactersInString:@"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-"];
        letters = [letters invertedSet];
        NSRange range = [string rangeOfCharacterFromSet:letters];
        if (range.location != NSNotFound) {
            //has invalid characters
            return NO;
        } else {
            return YES;
        }
        
    } else {
        //field is empty
        return NO;
    }
}


#pragma mark -
#pragma mark Views

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.

    //init the array
    self.typeArray = @[@"Home Phone", @"Cell Phone", @"Work"];
    type = @"Home Phone";
    
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//toast alert message
-(void)toastMessage {
    NSString *message = @"Contact Saved";
    UIAlertView *toast = [[UIAlertView alloc] initWithTitle:nil
                                                    message:message
                                                   delegate:nil
                                          cancelButtonTitle:nil
                                          otherButtonTitles:nil, nil];
    [toast show];
    int duration = 1.5;
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, duration
                                 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
        [toast dismissWithClickedButtonIndex:0 animated:YES];
    });
}


#pragma mark -
#pragma mark Navigation

//Save Button
-(IBAction)OnSave{
    //If NO Internet alert user
    if(![self Internet]){
        //send alert if fields are blank
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"No Internet Connection"
                                                       message:@"Must Have Internet to Save Contact"
                                                      delegate:nil
                                             cancelButtonTitle:@"Okay"
                                             otherButtonTitles:nil, nil];
        [alert show];

    }
    //Internet
    else {
        //check to see if any fields are empty
        if((![self isString:first.text])||(![self isString:last.text])){
            //send alert if fields are blank
            UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Alert"
                                                           message:@"Please make sure First & Last name fields are not blank. No Symbols or Spaces. (Example: John)"
                                                          delegate:nil
                                                 cancelButtonTitle:@"Okay"
                                                 otherButtonTitles:nil, nil];
            [alert show];
        }
        //check to see if number is correct
        else if([self isPhoneNumber:number.text]){
                //convert info to parse object
                PFObject *contact = [PFObject objectWithClassName:@"Contacts"];
                contact[@"FirstName"] = first.text;
                contact[@"LastName"] = last.text;
                contact[@"Type"] = type;

                //convert number string into NSNumber
                int num = [number.text intValue];
                NSNumber *phone = [NSNumber numberWithInt:num];
                contact[@"Phone"] = phone;
                
                //save user
                contact[@"User"] = [PFUser currentUser];
                [contact pinInBackground];
                [contact saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
                    if (succeeded) {
                        //send alert
                        [self toastMessage];
                        //Load Home Screen
                        UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                             bundle:nil];
                        HomeViewController *home =
                        [storyboard instantiateViewControllerWithIdentifier:@"home"];
                        
                        [self presentViewController:home
                                           animated:YES
                                         completion:nil];
                    } else {
                        NSLog(@"object didnt saved");
                    }
                }];

            }
        //number is incorrect send alert
        else {
            //send alert if number not right
            UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Phone Number is incorrect"
                                                           message:@"Please enter a 10 digit phone number (Example:7048675309)"
                                                          delegate:nil
                                                 cancelButtonTitle:@"Okay"
                                                 otherButtonTitles:nil, nil];
                [alert show];
        }
    }
}
//Cancel Button
-(IBAction)OnCancel{
    [self dismissViewControllerAnimated:true completion:nil];
}

#pragma mark -
#pragma mark PickerView
//dataSource
- (NSInteger)numberOfComponentsInPickerView: (UIPickerView *)pickerView {
    return 1;
}
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    return  typeArray.count;
}
- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    return typeArray[row];
}

//delegate
-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    //whatever user chose
    type = typeArray[row];
}
@end
