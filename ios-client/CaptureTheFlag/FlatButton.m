//
//  FlatButton.m
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "FlatButton.h"

@implementation FlatButton

- (void)initialize
{
}

- (id)init
{
    self = [super init];
    if (self) {
        [self initialize];
    }
    return self;
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self initialize];
    }
    return self;
}

- (id)initWithCoder:(NSCoder *)aDecoder
{
    self = [super initWithCoder:aDecoder];
    if (self) {
        [self initialize];
    }
    return self;
}

- (void)setHighlighted:(BOOL)highlighted
{
    [super setHighlighted:highlighted];
    self.backgroundColor = highlighted ? _buttonHighlightedBackgroundColor : _buttonBackgroundColor;
    [self setNeedsDisplay];
}

- (void)setButtonBackgroundColor:(UIColor *)color
{
    self.backgroundColor = color;
    _buttonBackgroundColor = color;
    [self setNeedsDisplay];
}

@end
