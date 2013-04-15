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

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


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
                                      [self performSegueWithIdentifier:@"segueToMainScreenAfterLogin" sender:self];

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

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"segueToMainScreenAfterLogin"])
    {
        
    }
}


@end
