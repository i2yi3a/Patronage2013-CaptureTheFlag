//
//  ShowInformation.m
//  CaptureTheFlag
//
//  Created by Milena Gnoińska on 15.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "ShowInformation.h"

@implementation ShowInformation

+ (void)showError:(NSString *)error
{
    
    UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Error"
                                                      message:error
                                                     delegate:nil
                                            cancelButtonTitle:@"OK"
                                            otherButtonTitles:nil];
    [message show];
}

+ (void)showMessage:(NSString *)message withTitle:(NSString *)title
{
    UIAlertView *pop_up = [[UIAlertView alloc] initWithTitle:title
                                                     message:message
                                                    delegate:nil
                                           cancelButtonTitle:@"OK"
                                           otherButtonTitles:nil];
    [pop_up show];
 
}
@end
