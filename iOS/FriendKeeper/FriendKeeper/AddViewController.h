//
//  AddViewController.h
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

@interface AddViewController : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource>
{
    IBOutlet UITextField *first;
    IBOutlet UITextField *last;
    IBOutlet UITextField *number;
    IBOutlet UIPickerView *picker;
    NSString *type;
}
-(IBAction)OnSave;
-(IBAction)OnCancel;

@property (strong, nonatomic)NSArray *typeArray;
@end
