//
//  ContactObject.h
//  FriendKeeper
//
//  Created by Kristie Syda on 2/9/16.
//  Copyright © 2016 Kristie Syda. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Parse/Parse.h>

@interface ContactObject : NSObject
@property(nonatomic,strong)NSString *first;
@property(nonatomic,strong)NSString *last;
@property(nonatomic,strong)PFObject *objectId;
@property(nonatomic,strong)NSNumber *phone;
@property(nonatomic,strong)NSString *type;
@end
