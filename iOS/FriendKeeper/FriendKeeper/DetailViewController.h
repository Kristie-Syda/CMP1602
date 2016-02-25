//
//  DetailViewController.h
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ContactObject.h"
#import "HomeViewController.h"
#import <Parse/Parse.h>

@interface DetailViewController : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource>
{
    IBOutlet UITextField *first;
    IBOutlet UITextField *last;
    IBOutlet UITextField *number;
    NSString *type;
}

-(IBAction)onDelete;
-(IBAction)onOkay;
-(IBAction)back;
@property(nonatomic,strong)ContactObject *current;
@property (strong, nonatomic)NSArray *typeArray;
@property(strong,nonatomic)IBOutlet UIPickerView *picker;
@end
