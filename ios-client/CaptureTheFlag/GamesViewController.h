//
//  GamesViewController.h
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 08.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GamesViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>{
}

@property (nonatomic, strong) NSArray *GamesViewCellRoundImages;
@property (nonatomic, strong) NSArray *GamesViewCellTitleLabels;
@property (nonatomic, strong) NSArray *GamesViewCellLocationLabels;
@property (nonatomic, strong) NSArray *GamesViewCellTimeLabels;


@end
