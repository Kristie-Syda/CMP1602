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
#import "ContactObject.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //Init Array
    contacts = [[NSMutableArray alloc]init];
    
    //Get current user info
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        welcome.text = currentUser.username;
    } else {
        welcome.text = @"Welcome!";
    }
    
    //clear array & get new data
    [contacts removeAllObjects];

    //Get user data from parse
    PFQuery *query = [PFQuery queryWithClassName:@"Contacts"];
    [query whereKey:@"User" equalTo:currentUser];
    [query findObjectsInBackgroundWithBlock:^(NSArray *allContacts, NSError *error) {
        if (!error) {
            //Loop through all contacts on parse
            for (PFObject *object in allContacts) {
                //Make objects into custom Contact Objects
                ContactObject *contact = [[ContactObject alloc] init];
                contact.first = object[@"FirstName"];
                contact.last = object[@"LastName"];
                contact.phone = object[@"Phone"];
                
                //Add objects to array
                [contacts addObject:contact];
            }
            [myTable reloadData];
        } else {
            NSLog(@"Error");
        }
    }];
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
    return [contacts count];
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    UITableViewCell *cell = [myTable dequeueReusableCellWithIdentifier:@"myCell"];
    if (cell != nil) {
        ContactObject *object = [contacts objectAtIndex:indexPath.row];
        
        //make first & last name together
        NSString *name = [NSString stringWithFormat:@"%@, %@",[object last],[object first]];
        cell.textLabel.text = name;
        
        //turn number into string
        NSString *number = [[object phone] stringValue];
        cell.detailTextLabel.text = number;
    }
    return cell;
}

@end
