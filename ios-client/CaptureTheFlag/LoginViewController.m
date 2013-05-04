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
@property (nonatomic, retain) UIAlertView *loginAlertView;
@end
@implementation LoginViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
    
    [self.view addGestureRecognizer:tap];
	// Do any additional setup after loading the view, typically from a nib.
    self.loginAlertView = [[UIAlertView alloc] initWithTitle:@"Please wait" message:nil
                                                       delegate:self
                                              cancelButtonTitle:nil
                                              otherButtonTitles:nil, nil];
    
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
                                      [_loginAlertView dismissWithClickedButtonIndex:0 animated:YES];
                                      [ShowInformation showError:@"Incorrect user name or password"];                                  }
                                  else
                                  {
                                      [self performSegueWithIdentifier:@"segueToMainScreenAfterLogin" sender:self];
                                      [_loginAlertView dismissWithClickedButtonIndex:0 animated:YES];
                                  }
                              }];
}


- (IBAction)login:(id)sender{
        [self beginLogin];
        [self loginWithUserEmail:self.userEmailField.text andPassword:self.passwordField.text];
    [_loginAlertView show];
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


- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField == self.userEmailField) {
        [textField resignFirstResponder];
        [self.passwordField becomeFirstResponder];
    }
    else if (textField == self.passwordField) {
        [textField resignFirstResponder];
    }
    return YES;
}

-(void)dismissKeyboard {
    [_userEmailField resignFirstResponder];
    [_passwordField resignFirstResponder];
}

@end
