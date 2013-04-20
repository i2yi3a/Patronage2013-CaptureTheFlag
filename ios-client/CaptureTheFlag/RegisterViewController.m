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
@end

@implementation RegisterViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
    
    [self.view addGestureRecognizer:tap];
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
                                  [ShowInformation showError:@"Failed to register new user"];
                              }
                              else
                              {
                                  [ShowInformation showError:@"Your registration proces is complete"];
                              }
                          }];
}


- (IBAction)reginster:(id)sender{ 
if ([self.passwordField.text isEqualToString:self.passwordField2.text])
{
    [self beginReginster];
    [self reginsterWithUserEmail:self.userEmailField.text andPassword:self.passwordField.text];
}
else
{
    [ShowInformation showError:@"passwords didn't match"];
}
}

- (IBAction)cancellRegister:(id)sender{
    [self dismissViewControllerAnimated:YES completion:nil];
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
    }
    return YES;
    }

-(void)dismissKeyboard {
    [_userEmailField resignFirstResponder];
    [_passwordField resignFirstResponder];
    [_passwordField2 resignFirstResponder];
}

@end

