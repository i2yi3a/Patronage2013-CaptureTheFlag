//
//  MainTableViewCell.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 28.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "MainTableViewCell.h"

@implementation MainTableViewCell

@synthesize mainViewControllerCellLabel = _mainViewControllerCellLabel;
@synthesize mainViewControllerCellImage = _mainViewControllerCellImage;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
