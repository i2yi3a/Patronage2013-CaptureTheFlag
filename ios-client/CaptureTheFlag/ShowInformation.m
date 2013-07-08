//
//  ShowInformation.m
//  CaptureTheFlag
//
//  Created by Milena Gnoińska on 15.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "ShowInformation.h"
#import "CustomAlert.h"

@implementation ShowInformation

+ (void)showError:(NSString *)error inView:(UIView *)view
{
    CustomAlert *alert = [[CustomAlert alloc] initWithTitle:@"Error"
                                                    message:error delegate:self
                                          cancelButtonTitle:nil otherButtonTitle:@"ok"];
    [alert showInView:view];
}

+ (void)showMessage:(NSString *)message withTitle:(NSString *)title inView:(UIView *)view
{
    CustomAlert *alert = [[CustomAlert alloc] initWithTitle:title
                                                    message:message delegate:self
                                          cancelButtonTitle:nil otherButtonTitle:@"ok"];
    [alert showInView:view];
}

+ (CustomAlert *)showLoading: (UIView *)view
{
    CustomAlert *alert = [[CustomAlert alloc] initWithTitle:@"LOADING" 
                                                    message:nil
                                                   delegate:self
                                          cancelButtonTitle:nil otherButtonTitle:nil];
    [alert showInView:view];
    return alert;
}
+ (void)dissmisLoading:(CustomAlert*)alert{
    [alert animateHide];
}
@end
