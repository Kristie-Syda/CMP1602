//
//  HomeViewController.h
//  FriendKeeper
//
//  Created by Kristie Syda on 2/8/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

@interface HomeViewController : UIViewController <UITableViewDataSource,UITableViewDelegate>
{
    IBOutlet UILabel *welcome;
    IBOutlet UITableView *myTable;
    NSMutableArray *contacts;
    PFUser *currentUser;
}

-(IBAction)logOut;
-(IBAction)add;
-(IBAction)refresh;
@end
