//
//  MainViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 12.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//


 #import "LoginViewController.h"


@interface LoginViewController ()
@property (weak, nonatomic) IBOutlet UITextField *userEmailField;
@property (weak, nonatomic) IBOutlet UITextField *passwordField;
@end
@implementation LoginViewController

-(void)loginWithUserEmail:(NSString *)userEmail andPassword:(NSString *)password
{
    [[NetworkEngine getInstance]
                                 loginWithEmail:userEmail
                                 withPassword:(NSString *)password
                                 completionBlock:^(NSObject *response) {
                                  if ([response isKindOfClass:[NSError class]])
                                  {
                                      UIAlertView *loginErrorAlert = [[UIAlertView alloc]
                                                                          initWithTitle:@"UIAlertView" message:@"error" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                                      [loginErrorAlert show];
                                      
                                      
                                  }
                                  else
                                  {
                                     //przejscie na ekran glowny
                                  }
                              }];
}


- (IBAction)login:(id)sender{
        [self beginLogin];
        [self loginWithUserEmail:self.userEmailField.text andPassword:self.passwordField.text];
}

-(void)beginLogin

{
    [self.userEmailField resignFirstResponder];
    [self.passwordField resignFirstResponder];
}

@end
