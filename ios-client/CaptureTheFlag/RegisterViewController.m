//
//  ViewController.m
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "RegisterViewController.h"

@interface RegisterViewController ()
@property (weak, nonatomic) IBOutlet UITextField *userEmailField; 
@property (weak, nonatomic) IBOutlet UITextField *passwordField; 
@property (weak, nonatomic) IBOutlet UITextField *passwordField2;
@property (nonatomic, retain) UIAlertView *loginAlertView;
@end

@implementation RegisterViewController

- (IBAction)switchcontrol:(id)sender{
    if (control.selectedSegmentIndex == 0) {
        [self performSegueWithIdentifier:@"segueToMainScreenAfterRegister" sender:self];
    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
    
    [self.view addGestureRecognizer:tap];
    self.loginAlertView = [[UIAlertView alloc] initWithTitle:@"Please wait" message:nil
                                                    delegate:self
                                           cancelButtonTitle:nil
                                           otherButtonTitles:nil, nil];

	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(void)reginsterWithUserEmail:(NSString *)userEmail andPassword:(NSString *)password
{
    [[NetworkEngine getInstance] registerUser:userEmail
                             withPassword:(NSString *)password
                          completionBlock:^(NSObject *response) {
                              if ([response isKindOfClass:[NSError class]])
                              {
                                  [_loginAlertView dismissWithClickedButtonIndex:0 animated:YES];
                                  [ShowInformation showError:@"Failed to register new user"];
                              }
                              else
                              {
                                  [_loginAlertView dismissWithClickedButtonIndex:0 animated:YES];
                                  [ShowInformation showMessage:@"Your registration proces is complete" withTitle:nil];
                                  [self performSegueWithIdentifier:@"segueToMainScreenAfterRegister" sender:self];
                              }
                          }];
}


- (IBAction)reginster:(id)sender{
            [_loginAlertView show];
if ([self.passwordField.text isEqualToString:self.passwordField2.text])
{
    [self beginReginster];
    [self reginsterWithUserEmail:self.userEmailField.text andPassword:self.passwordField.text];
}
else
{
    [ShowInformation showError:@"passwords didn't match"];
    [_loginAlertView dismissWithClickedButtonIndex:0 animated:YES];
}
}


-(void)beginReginster

{
    [self.userEmailField resignFirstResponder];
    [self.passwordField resignFirstResponder];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField == self.userEmailField) {
        [textField resignFirstResponder];
        [self.passwordField becomeFirstResponder];
    }
        else if (textField == self.passwordField) {
            [textField resignFirstResponder];
            [self.passwordField2 becomeFirstResponder];
        }
        else if (textField == self.passwordField2) {
            [textField resignFirstResponder];
            [_loginAlertView show];
            if ([self.passwordField.text isEqualToString:self.passwordField2.text])
            {
                [self beginReginster];
                [self reginsterWithUserEmail:self.userEmailField.text andPassword:self.passwordField.text];
            }
            else
            {
                [ShowInformation showError:@"passwords didn't match"];
                [_loginAlertView dismissWithClickedButtonIndex:0 animated:YES];
            }

    }
    return YES;
    }

-(void)dismissKeyboard {
    [_userEmailField resignFirstResponder];
    [_passwordField resignFirstResponder];
    [_passwordField2 resignFirstResponder];
}
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"segueToMainScreenAfterRegister"])
    {
        
    }
}

@end

