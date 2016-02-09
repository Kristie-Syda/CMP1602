//
//  HomeViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/8/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "HomeViewController.h"
#import "AddViewController.h"
#import "ViewController.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //Get current user info
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        welcome.text = currentUser.username;
    } else {
        welcome.text = @"Welcome!";
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//Log Out Button
-(IBAction)logOut{
    //Log user out of parse
    [PFUser logOut];
    PFUser *currentUser = [PFUser currentUser];
    NSLog(@"currentUser == %@",currentUser);
    
    //Go back to login screen
    UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                         bundle:nil];
    ViewController *view =
    [storyboard instantiateViewControllerWithIdentifier:@"main"];
    
    [self presentViewController:view
                       animated:YES
                     completion:nil];
}

//Add Contact Button
-(IBAction)add{
    UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                         bundle:nil];
    AddViewController *add =
    [storyboard instantiateViewControllerWithIdentifier:@"add"];
    
    [self presentViewController:add
                       animated:YES
                     completion:nil];

}

//TableView Required Methods
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 0;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    return nil;
}

@end
