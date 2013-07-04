//
//  CustomAlert.h
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 04.07.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@interface CustomAlert : UIView
{
    id delegate;
    UIView *AlertView;
}
@property id delegate;

- (id)initWithTitle:(NSString *)title message:(NSString *)message delegate:(id)AlertDelegate cancelButtonTitle:(NSString *)cancelButtonTitle otherButtonTitle:(NSString *)otherButtonTitle;
- (void)showInView:(UIView*)view;
@end

@protocol CustomAlertDelegate
- (void)customAlertView:(CustomAlert*)alertView clickedButtonAtIndex:(NSInteger)buttonIndex;
@end