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
#import "DetailViewController.h"
#import "ContactObject.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

#pragma mark -
#pragma mark Managing Views
- (void)viewDidLoad {
    [super viewDidLoad];
    
    //Init Array
    contacts = [[NSMutableArray alloc]init];
    
    //Get current user info
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        welcome.text = [NSString stringWithFormat:@"Welcome %@", currentUser.username];
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
                contact.objectId = object;
                contact.type = object[@"Type"];
                
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
//toast alert message
-(void)toastMessage {
    NSString *message = @"User Logged Out";
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
#pragma mark Table Management

//TableView row count
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [contacts count];
}
//TableView cells
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

#pragma mark -
#pragma mark Navigation

//Log Out Button
-(IBAction)logOut{
    //send alert
    [self toastMessage];
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
//Segue
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    DetailViewController *detailView = segue.destinationViewController;
    if (detailView != nil) {
        UITableViewCell *cell = (UITableViewCell*)sender;
        NSIndexPath *indexPath = [myTable indexPathForCell:cell];
        
        //pass object to the detail view controller
        ContactObject *current = [contacts objectAtIndex:indexPath.row];
        detailView.current = current;
    }
}

@end
