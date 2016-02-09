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

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)signUp {
    PFUser *user = [PFUser user];
    user.username = userName.text;
    user.password = password.text;
    user.email = email.text;
    
    [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (!error) {
            UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                 bundle:nil];
            HomeViewController *home =
            [storyboard instantiateViewControllerWithIdentifier:@"home"];
            
            [self presentViewController:home
                               animated:YES
                             completion:nil];
        } else {   NSString *errorString = [error userInfo][@"error"];
            NSLog(@"Error: %@ ", errorString);
        }
    }];
}

-(IBAction)onSubmit{
    [self signUp];
}

@end
