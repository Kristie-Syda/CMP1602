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

@interface DetailViewController : UIViewController
{
    IBOutlet UILabel *first;
    IBOutlet UILabel *last;
    IBOutlet UILabel *number;
}

-(IBAction)onDelete;
-(IBAction)onOkay;
@property(nonatomic,strong)ContactObject *current;
@end
