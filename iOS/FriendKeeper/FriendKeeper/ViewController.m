//
//  ViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/8/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "ViewController.h"
#import "HomeViewController.h"

@interface ViewController ()

@end

@implementation ViewController

#pragma mark -
#pragma mark Managing Views
- (void)viewDidLoad {
    [super viewDidLoad];
}

//present home screen if user is still logged in
-(void)viewDidAppear:(BOOL)animated{
    //Get current user info
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        //send toast of logged in already
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
        NSLog(@"no currentUser");
    }

}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//toast alert message of log in
-(void)toastMessage {
    NSString *message = @"User Logged In";
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

//Log in Button
-(IBAction)onLogIn {
    //present Home screen
    [PFUser logInWithUsernameInBackground:userName.text password:password.text
                                    block:^(PFUser *user, NSError *error) {
                                        if (user) {
                                            //send toast of successful login
                                            [self toastMessage];
                                            //preset home screen
                                            UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                                                 bundle:nil];
                                            HomeViewController *home =
                                            [storyboard instantiateViewControllerWithIdentifier:@"home"];
                                            
                                            [self presentViewController:home
                                                               animated:YES 
                                                             completion:nil];
                                        } else {
                                            NSLog(@"not logged in");
                                            UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error"
                                                                                           message:[error userInfo][@"error"]
                                                                                          delegate:nil
                                                                                 cancelButtonTitle:@"Okay"
                                                                                 otherButtonTitles:nil, nil];
                                            [alert show];
                                        }
                                    }];
}

@end
