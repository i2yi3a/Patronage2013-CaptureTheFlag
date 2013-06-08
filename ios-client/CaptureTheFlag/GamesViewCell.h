//
//  GamesViewCell.h
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 08.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GamesViewCell : UITableViewCell

@property (nonatomic, strong) IBOutlet UIImageView *GamesViewCellRoundImage;
@property (nonatomic, strong) IBOutlet UILabel *GamesViewCellTitleLabel;
@property (nonatomic, strong) IBOutlet UILabel *GamesViewCellLocationLabel;
@property (nonatomic, strong) IBOutlet UILabel *GamesViewCellTimeLabel;

@end
