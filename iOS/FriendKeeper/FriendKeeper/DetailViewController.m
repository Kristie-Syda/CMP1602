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

- (void)viewDidLoad {
    
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

-(IBAction)onDelete{
    [self.current.objectId deleteInBackgroundWithBlock:^(BOOL succeeded, NSError * _Nullable error) {
        if (!error) {
            NSLog(@"delete complete");
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
