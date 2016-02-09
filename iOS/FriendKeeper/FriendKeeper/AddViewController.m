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

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//Save Button
-(IBAction)OnSave{
    if((first.text.length != 0)|(last.text.length != 0)|(number.text.length != 0)){
        PFObject *contact = [PFObject objectWithClassName:@"Contacts"];
        contact[@"FirstName"] = first.text;
        contact[@"LastName"] = last.text;
        
        //convert number string into NSNumber
        int num = [number.text intValue];
        NSNumber *phone = [NSNumber numberWithInt:num];
        contact[@"Phone"] = phone;
        
        //save user
        contact[@"User"] = [PFUser currentUser];
        [contact saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
            if (succeeded) {
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

@end
