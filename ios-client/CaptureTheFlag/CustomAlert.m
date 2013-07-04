//
//  CustomAlert.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 04.07.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "CustomAlert.h"
#define MAX_ALERT_HEIGHT 300.0

@implementation CustomAlert

@synthesize delegate;

- (id)initWithTitle:(NSString *)title message:(NSString *)message delegate:(id)AlertDelegate cancelButtonTitle:(NSString *)cancelButtonTitle otherButtonTitle:(NSString *)otherButtonTitle
{
    
    CGRect frame;
    if ([[UIApplication sharedApplication] statusBarOrientation] == UIDeviceOrientationLandscapeLeft || [[UIApplication sharedApplication] statusBarOrientation] == UIDeviceOrientationLandscapeRight)
        frame = CGRectMake(0.0, 0.0, [[UIScreen mainScreen] bounds].size.height, [[UIScreen mainScreen] bounds].size.width);
    else
        frame = CGRectMake(0.0, 0.0, [[UIScreen mainScreen] bounds].size.width, [[UIScreen mainScreen] bounds].size.height);
    
    self = [super initWithFrame:frame];
    if (self) {
        self.delegate = AlertDelegate;
        self.backgroundColor = [UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.6];
        self.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
        
        UIImageView *AlertImgView = [[UIImageView alloc] initWithFrame:CGRectMake(22.0, 120.0, 276.0, 44.0)];
        AlertImgView.backgroundColor=[UIColor ctfNormalButtonAndLabelTurquoiseColor];
        
        UIImageView *AlertImgTitleView = [[UIImageView alloc] initWithFrame:CGRectMake(0.0, 0.0, 276.0, 44.0)];
        AlertImgTitleView.backgroundColor=[UIColor ctfNormalButtonAndLabelCarrotColor];
        
        float alert_width = AlertImgView.frame.size.width;
        float alert_height = 8.0;
        
        UILabel *TitleLbl;
        UIScrollView *MsgScrollView;
        
        if (title)
        {
            TitleLbl = [[UILabel alloc] initWithFrame:CGRectMake(10.0, alert_height, alert_width-20.0, 30.0)];
            TitleLbl.adjustsFontSizeToFitWidth = YES;
            TitleLbl.font = [UIFont boldSystemFontOfSize:18.0];
            TitleLbl.textAlignment = UITextAlignmentCenter;
            TitleLbl.minimumFontSize = 12.0;
            TitleLbl.backgroundColor = [UIColor clearColor];
            TitleLbl.textColor = [UIColor whiteColor];
            TitleLbl.text = title;
            
            alert_height += TitleLbl.frame.size.height + 5.0;
        }
        else
            alert_height += 15.0;
        
        if (message)
        {
            float max_msg_height = MAX_ALERT_HEIGHT - alert_height - ((44.0)?(44+30.0):30.0);
            
            UILabel *MessageLbl = [[UILabel alloc] initWithFrame:CGRectMake(20.0, 10.0, alert_width-40.0, 0.0)];
            MessageLbl.numberOfLines = 0;
            MessageLbl.font = [UIFont systemFontOfSize:16.0];
            MessageLbl.textAlignment = UITextAlignmentCenter;
            MessageLbl.backgroundColor = [UIColor clearColor];
            MessageLbl.textColor = [UIColor whiteColor];
            MessageLbl.text = message;
            
            [MessageLbl sizeToFit];
            MessageLbl.frame = CGRectMake(10.0, 22.0, alert_width-40.0, MessageLbl.frame.size.height);
            
            while (MessageLbl.frame.size.height>max_msg_height && MessageLbl.font.pointSize>12) {
                MessageLbl.font = [UIFont systemFontOfSize:MessageLbl.font.pointSize-1];
                [MessageLbl sizeToFit];
                MessageLbl.frame = CGRectMake(10.0, 22.0, alert_width-40.0, MessageLbl.frame.size.height);
            }
            
            MsgScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(10.0, alert_height, alert_width-20.0, (MessageLbl.frame.size.height>max_msg_height)?max_msg_height:MessageLbl.frame.size.height+22)];
            MsgScrollView.contentSize = MessageLbl.frame.size;
            [MsgScrollView addSubview:MessageLbl];
            
            alert_height += MsgScrollView.frame.size.height + 30.0;
        }
        else
            alert_height += 15.0;
        
        FlatButton *CancelBtn;
        FlatButton *OtherBtn;
        
        if (cancelButtonTitle && otherButtonTitle)
        {
            float x_displ = (int)((alert_width-100*2)/3.0);
            CancelBtn = [[FlatButton alloc] initWithFrame:CGRectMake(x_displ, alert_height, 105, 44)];
            [CancelBtn setTag:1000];
            [CancelBtn setTitle:cancelButtonTitle forState:UIControlStateNormal];
            [CancelBtn.titleLabel setFont:[UIFont boldSystemFontOfSize:18.0]];
            [CancelBtn setBackgroundColor:[UIColor ctfApplicationBackgroundLighterColor]];
            CancelBtn.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
            [CancelBtn addTarget:self action:@selector(onBtnPressed:) forControlEvents:UIControlEventTouchUpInside];
            
            OtherBtn = [[FlatButton alloc] initWithFrame:CGRectMake(x_displ*2+105, alert_height, 105, 44)];
            [OtherBtn setTag:1001];
            [OtherBtn setTitle:otherButtonTitle forState:UIControlStateNormal];
            [OtherBtn.titleLabel setFont:[UIFont boldSystemFontOfSize:18.0]];
            [OtherBtn setBackgroundColor:[UIColor ctfNormalButtonAndLabelCarrotColor]];
            OtherBtn.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
            [OtherBtn addTarget:self action:@selector(onBtnPressed:) forControlEvents:UIControlEventTouchUpInside];
            
            alert_height += CancelBtn.frame.size.height + 15.0;
        }
        else if (cancelButtonTitle)
        {
            CancelBtn = [[FlatButton alloc] initWithFrame:CGRectMake(22, alert_height, 232, 44)];
            [CancelBtn setTag:1000];
            [CancelBtn setTitle:cancelButtonTitle forState:UIControlStateNormal];
            CancelBtn.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
            [CancelBtn.titleLabel setFont:[UIFont boldSystemFontOfSize:18.0]];
            [CancelBtn setBackgroundColor:[UIColor ctfApplicationBackgroundLighterColor]];
            [CancelBtn addTarget:self action:@selector(onBtnPressed:) forControlEvents:UIControlEventTouchUpInside];
            
            alert_height += CancelBtn.frame.size.height + 15.0;
        }
        else if (otherButtonTitle)
        {
            OtherBtn = [[FlatButton alloc] initWithFrame:CGRectMake((int)((alert_width-232)/2.0), alert_height,232, 44)];
            [OtherBtn setTag:1001];
            [OtherBtn setTitle:otherButtonTitle forState:UIControlStateNormal];
            [OtherBtn.titleLabel setFont:[UIFont boldSystemFontOfSize:18.0]];
            [OtherBtn setBackgroundColor:[UIColor ctfNormalButtonAndLabelCarrotColor]];
            OtherBtn.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
            [OtherBtn addTarget:self action:@selector(onBtnPressed:) forControlEvents:UIControlEventTouchUpInside];
            
            alert_height += OtherBtn.frame.size.height + 15.0;
        }
        else
            alert_height += 15.0;
        
        AlertView = [[UIView alloc] initWithFrame:CGRectMake((int)((self.frame.size.width-alert_width)/2.0), (int)((self.frame.size.height-alert_height)/2.0), alert_width, alert_height)];
        AlertView.autoresizingMask = UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleTopMargin | UIViewAutoresizingFlexibleBottomMargin;
        AlertImgView.frame = AlertView.bounds;
        [AlertView addSubview:AlertImgView];
        [AlertView addSubview:AlertImgTitleView];
        
        [self addSubview:AlertView];
        
        if (TitleLbl)
            [AlertView addSubview:TitleLbl];
        
        if (MsgScrollView)
            [AlertView addSubview:MsgScrollView];
        
        if (CancelBtn)
            [AlertView addSubview:CancelBtn];
        
        if (OtherBtn)
            [AlertView addSubview:OtherBtn];
    }
    return self;
}

- (void)onBtnPressed:(id)sender
{
    FlatButton *button = (FlatButton *)sender;
    
    int button_index = button.tag-1000;
    
    if ([delegate respondsToSelector:@selector(customAlertView:clickedButtonAtIndex:)])
        [delegate customAlertView:self clickedButtonAtIndex:button_index];
    
    [self animateHide];
}

- (void)showInView:(UIView*)view
{
    if ([view isKindOfClass:[UIView class]])
    {
        [view addSubview:self];
        [self animateShow];
    }
}

- (void)animateShow
{
    CAKeyframeAnimation *animation = [CAKeyframeAnimation
                                      animationWithKeyPath:@"transform"];
    
    CATransform3D scale1 = CATransform3DMakeScale(0.5, 0.5, 1);
    CATransform3D scale2 = CATransform3DMakeScale(1.2, 1.2, 1);
    CATransform3D scale3 = CATransform3DMakeScale(0.9, 0.9, 1);
    CATransform3D scale4 = CATransform3DMakeScale(1.0, 1.0, 1);
    
    NSArray *frameValues = [NSArray arrayWithObjects:
                            [NSValue valueWithCATransform3D:scale1],
                            [NSValue valueWithCATransform3D:scale2],
                            [NSValue valueWithCATransform3D:scale3],
                            [NSValue valueWithCATransform3D:scale4],
                            nil];
    [animation setValues:frameValues];
    
    NSArray *frameTimes = [NSArray arrayWithObjects:
                           [NSNumber numberWithFloat:0.0],
                           [NSNumber numberWithFloat:0.5],
                           [NSNumber numberWithFloat:0.9],
                           [NSNumber numberWithFloat:1.0],
                           nil];
    [animation setKeyTimes:frameTimes];
    
    animation.fillMode = kCAFillModeForwards;
    animation.removedOnCompletion = NO;
    animation.duration = 0.2;
    
    [AlertView.layer addAnimation:animation forKey:@"show"];
}

- (void)animateHide
{
    CAKeyframeAnimation *animation = [CAKeyframeAnimation
                                      animationWithKeyPath:@"transform"];
    
    CATransform3D scale1 = CATransform3DMakeScale(1.0, 1.0, 1);
    CATransform3D scale2 = CATransform3DMakeScale(0.5, 0.5, 1);
    CATransform3D scale3 = CATransform3DMakeScale(0.0, 0.0, 1);
    
    NSArray *frameValues = [NSArray arrayWithObjects:
                            [NSValue valueWithCATransform3D:scale1],
                            [NSValue valueWithCATransform3D:scale2],
                            [NSValue valueWithCATransform3D:scale3],
                            nil];
    [animation setValues:frameValues];
    
    NSArray *frameTimes = [NSArray arrayWithObjects:
                           [NSNumber numberWithFloat:0.0],
                           [NSNumber numberWithFloat:0.5],
                           [NSNumber numberWithFloat:0.9],
                           nil];
    [animation setKeyTimes:frameTimes];
    
    animation.fillMode = kCAFillModeForwards;
    animation.removedOnCompletion = NO;
    animation.duration = 0.1;
    
    [AlertView.layer addAnimation:animation forKey:@"hide"];
    
    [self performSelector:@selector(removeFromSuperview) withObject:self afterDelay:0.105];
}

@end

