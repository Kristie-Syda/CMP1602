//
//  AddViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "AddViewController.h"

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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

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
                NSLog(@"object saved");
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
