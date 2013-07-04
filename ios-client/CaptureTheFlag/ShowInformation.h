//
//  ShowInformation.h
//  CaptureTheFlag
//
//  Created by Milena Gnoińska on 15.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CustomAlert.h"

@interface ShowInformation : UIViewController <CustomAlertDelegate>

+ (void)showError:(NSString *)error inView:(UIView *)view;
+ (void)showMessage:(NSString *)message withTitle:(NSString *)title inView:(UIView *)view;
+ (CustomAlert *)showLoading: (UIView *)view;
+ (void)dissmisLoading:(CustomAlert*)alert;
@end
