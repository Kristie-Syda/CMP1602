//
//  SignUpViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/8/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "SignUpViewController.h"
#import "HomeViewController.h"

@interface SignUpViewController ()

@end

@implementation SignUpViewController

#pragma mark -
#pragma mark Views
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark -
#pragma mark Parse Sign Up
//Sign Up to parse
- (void)signUp {
    
    //convert info to parse user
    PFUser *user = [PFUser user];
    user.username = userName.text;
    user.password = password.text;
    user.email = email.text;
    
    //check to see if required fields are blank
    if ((password.text.length == 0)||(email.text.length == 0)) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error"
                                                       message:@"Username, Email & password are required fields"
                                                      delegate:nil
                                             cancelButtonTitle:@"Okay"
                                             otherButtonTitles:nil, nil];
        [alert show];
    } else {
        [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
            if (!error) {
                //send toast
                [self toastMessage];
                //present home screen
                UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                     bundle:nil];
                HomeViewController *home =
                [storyboard instantiateViewControllerWithIdentifier:@"home"];
                
                [self presentViewController:home
                                   animated:YES
                                 completion:nil];
            } else {
                //send alert of error
                NSString *errorString = [error userInfo][@"error"];
                NSLog(@"Error: %@ ", errorString);
                UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error"
                                                               message:errorString
                                                              delegate:nil
                                                     cancelButtonTitle:@"Okay"
                                                     otherButtonTitles:nil, nil];
                [alert show];
            }
        }];
    }
}
//toast alert message
-(void)toastMessage {
    NSString *message = @"User Created";
    UIAlertView *toast = [[UIAlertView alloc] initWithTitle:nil
                                                    message:message
                                                   delegate:nil
                                          cancelButtonTitle:nil
                                          otherButtonTitles:nil, nil];
    toast.backgroundColor=[UIColor redColor];
    [toast show];
    int duration = 2; // duration in seconds
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, duration
                                 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
        [toast dismissWithClickedButtonIndex:0 animated:YES];
    });
}

#pragma mark -
#pragma mark Navigation

-(IBAction)onSubmit{
    [self signUp];
}
-(IBAction)onCancel{
    [self dismissViewControllerAnimated:true completion:nil];
}
@end
