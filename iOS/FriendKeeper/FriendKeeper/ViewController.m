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
#pragma mark Managing Views
- (void)viewDidLoad {
    [super viewDidLoad];
    
    //Only send alert user if no internet
    if(![self Internet]){
        [self toastMessage:@"No Internet Connection"];
    }
}
//present home screen if user is still logged in
-(void)viewDidAppear:(BOOL)animated{
    //Get current user info
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        //present home screen
        UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                             bundle:nil];
        HomeViewController *home =
        [storyboard instantiateViewControllerWithIdentifier:@"home"];
        
        [self presentViewController:home
                           animated:YES
                         completion:nil];

    } else {
        
    }

}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//toast alert message of log in
-(void)toastMessage:(NSString *)msg {
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

//Log in Button
-(IBAction)onLogIn {
    if ([self Internet]) {
        // Log in to parse && present Home screen
        [PFUser logInWithUsernameInBackground:userName.text password:password.text
                                        block:^(PFUser *user, NSError *error) {
                                            if (user) {
                                                //send toast of successful login
                                                [self toastMessage:@"User Logged In"];
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

    } else {
        //send alert if fields are blank
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"No Internet Connection"
                                                       message:@"Must Have Internet to Login"
                                                      delegate:nil
                                             cancelButtonTitle:@"Okay"
                                             otherButtonTitles:nil, nil];
        [alert show];
    }
}

@end
