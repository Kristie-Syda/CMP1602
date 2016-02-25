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
#pragma mark Views

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    //init the array
    self.typeArray = @[@"Home Phone", @"Cell Phone", @"Work"];
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
    //check to see if any fields are empty
    if((first.text.length == 0)||(last.text.length == 0)||(number.text.length == 0)){
        //send alert if fields are blank
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Alert"
                                                       message:@"Please fill in all fields"
                                                      delegate:nil
                                             cancelButtonTitle:@"Okay"
                                             otherButtonTitles:nil, nil];
        [alert show];
    } else {
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
    NSLog(@"%@",type);
}
@end
