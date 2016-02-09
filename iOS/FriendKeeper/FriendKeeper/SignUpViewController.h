//
//  SignUpViewController.h
//  FriendKeeper
//
//  Created by Kristie Syda on 2/8/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

@interface SignUpViewController : UIViewController
{
    IBOutlet UITextField *userName;
    IBOutlet UITextField *password;
    IBOutlet UITextField *email;
    IBOutlet UITextField *firstName;
    IBOutlet UITextField *lastName;
}

-(IBAction)onSubmit;
-(IBAction)onCancel;
@end
