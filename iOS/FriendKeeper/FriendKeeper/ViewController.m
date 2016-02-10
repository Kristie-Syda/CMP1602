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

- (void)viewDidLoad {
    [super viewDidLoad];
}

//present home screen if user is still logged in
-(void)viewDidAppear:(BOOL)animated{
    //Get current user info
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
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

//Log in Button
-(IBAction)onLogIn {
    //present Home screen
    [PFUser logInWithUsernameInBackground:userName.text password:password.text
                                    block:^(PFUser *user, NSError *error) {
                                        if (user) {
                                            UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                                                 bundle:nil];
                                            HomeViewController *home =
                                            [storyboard instantiateViewControllerWithIdentifier:@"home"];
                                            
                                            [self presentViewController:home
                                                               animated:YES 
                                                             completion:nil];
                                        } else {
                                            NSLog(@"not logged in"); 
                                        }
                                    }];
}

@end
