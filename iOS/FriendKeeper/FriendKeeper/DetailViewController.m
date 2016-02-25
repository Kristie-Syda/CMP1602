//
//  DetailViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "DetailViewController.h"

@interface DetailViewController ()

@end

@implementation DetailViewController
@synthesize current,typeArray,picker;

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

#pragma mark -
#pragma mark Views
- (void)viewDidLoad {
    //set text to textfields
    first.text = self.current.first;
    last.text = self.current.last;
    
    //init the array
    self.typeArray = @[@"Home Phone", @"Cell Phone", @"Work"];
   
    //get type in parse and set picker
    if([self.current.type isEqualToString:@"Home Phone"]){
        type = @"Home Phone";
        [self.picker selectRow:0 inComponent:0 animated:NO];
    } else if([self.current.type isEqualToString:@"Cell Phone"]){
        type = @"Cell Phone";
        [self.picker selectRow:1 inComponent:0 animated:NO];
    } else {
        type = @"Work";
        [self.picker selectRow:2 inComponent:0 animated:NO];
    }
    
    //turn number into string
    NSString *num = [self.current.phone stringValue];
    number.text = num;
    
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//toast alert message
-(void)toastMessage:(NSString*)msg {
    UIAlertView *toast = [[UIAlertView alloc] initWithTitle:nil
                                                    message:msg
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
//Delete button
-(IBAction)onDelete{
    if(![self Internet]){
        //Send Alert
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"No Internet Connection"
                                                       message:@"Must Have Internet to Delete Contact"
                                                      delegate:nil
                                             cancelButtonTitle:@"Okay"
                                             otherButtonTitles:nil, nil];
        [alert show];
        
    } else {

        //delete parse object from parse data base
        [self.current.objectId unpinInBackground];
        [self.current.objectId deleteInBackgroundWithBlock:^(BOOL succeeded, NSError * _Nullable error) {
            if (!error) {
                //Present home screen
                [self toastMessage:@"Contact Deleted"];
                UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                     bundle:nil];
                HomeViewController *home =
                [storyboard instantiateViewControllerWithIdentifier:@"home"];
            
                [self presentViewController:home
                                   animated:YES
                                 completion:nil];
            } else {
                NSLog(@"ERROR");
            }
        }];
    }
}
//Back button
-(IBAction)back{
    [self dismissViewControllerAnimated:true completion:nil];
}
//Okay button
-(IBAction)onOkay{
    if(![self Internet]){
        //Send Alert
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"No Internet Connection"
                                                       message:@"Must Have Internet to Update Contact"
                                                      delegate:nil
                                             cancelButtonTitle:@"Okay"
                                             otherButtonTitles:nil, nil];
        [alert show];

    } else {
        PFQuery *query = [PFQuery queryWithClassName:@"Contacts"];
        
        // Retrieve the object by id
        [query getObjectInBackgroundWithId:self.current.objectId.objectId
                                     block:^(PFObject *contact, NSError *error) {
                                         contact[@"FirstName"] = first.text;
                                         contact[@"LastName"] = last.text;
                                         contact[@"Type"] = type;
                                         
                                         //convert number string into NSNumber
                                         int num = [number.text intValue];
                                         NSNumber *phone = [NSNumber numberWithInt:num];
                                         contact[@"Phone"] = phone;
                                         
                                         [contact saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
                                             if (succeeded) {
                                                 //send alert
                                                 [self toastMessage:@"contact updated"];
                                                 //Load Home Screen
                                                 UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                                                      bundle:nil];
                                                 HomeViewController *home =
                                                 [storyboard instantiateViewControllerWithIdentifier:@"home"];
                                                 
                                                 [self presentViewController:home
                                                                    animated:YES
                                                                  completion:nil];
                                             } else {
                                                 NSLog(@"object didnt update");
                                             }
                                         }];
                                         
                                     }];

    }
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
