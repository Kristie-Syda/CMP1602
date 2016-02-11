//
//  DetailViewController.m
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import "DetailViewController.h"

@interface DetailViewController ()

@end

@implementation DetailViewController
@synthesize current;

#pragma mark -
#pragma mark Views
- (void)viewDidLoad {
    //set text to textfields
    first.text = self.current.first;
    last.text = self.current.last;
    
    //turn number into string
    NSString *num = [self.current.phone stringValue];
    number.text = num;
    
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//toast alert message
-(void)toastMessage {
    NSString *message = @"Contact Deleted";
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
#pragma mark Navigation
//Delete button
-(IBAction)onDelete{
    //delete parse object from parse data base
    [self.current.objectId deleteInBackgroundWithBlock:^(BOOL succeeded, NSError * _Nullable error) {
        if (!error) {
            //Present home screen
            [self toastMessage];
            UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main"
                                                                 bundle:nil];
            HomeViewController *home =
            [storyboard instantiateViewControllerWithIdentifier:@"home"];
            
            [self presentViewController:home
                               animated:YES
                             completion:nil];
        } else {
            NSLog(@"ERROR");
        }
    }];
}
//Okay button
-(IBAction)onOkay{
    [self dismissViewControllerAnimated:true completion:nil];
}

@end
