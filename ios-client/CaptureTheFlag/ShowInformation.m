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

/*+ (void)showError:(NSString *)error
{
    CustomAlert *alert = [[CustomAlert alloc] initWithTitle:@"Warning" message:@"eihteprj iregj peioeg joeireg hio" delegate:self cancelButtonTitle:@"cancel" otherButtonTitle:@"ok"];
    [alert showInView:RootViewController];
}
*/
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
