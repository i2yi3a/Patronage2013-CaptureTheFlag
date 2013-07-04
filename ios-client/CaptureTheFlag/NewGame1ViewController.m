//
//  NewGame1ViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 21.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NewGame1ViewController.h"
#import "NewGame2ViewController.h"
#import "CTFGame.h"
static const CGFloat KEYBOARD_ANIMATION_DURATION = 0.3;
static const CGFloat MINIMUM_SCROLL_FRACTION = 0.2;
static const CGFloat MAXIMUM_SCROLL_FRACTION = 0.8;
static const CGFloat PORTRAIT_KEYBOARD_HEIGHT = 216;

@interface NewGame1ViewController ()

@property (weak, nonatomic) IBOutlet UIView *nameBackgroundView;
@property (weak, nonatomic) IBOutlet UIView *descriptionBackgroundView;
@property (weak, nonatomic) IBOutlet UIView *redBackgroundView;
@property (weak, nonatomic) IBOutlet UIView *blueBackgroundView;
@property (weak, nonatomic) IBOutlet FlatButton *backButton;
@property (weak, nonatomic) IBOutlet FlatButton *nextButton;
@property (weak, nonatomic) IBOutlet UIView *topBar;
@property (readwrite) CGFloat animatedDistance;
@property (weak, nonatomic) IBOutlet UITextField *gameName;
@property (weak, nonatomic) IBOutlet UITextView *gameDescription;
@property (weak, nonatomic) IBOutlet UITextField *gameRedName;
@property (weak, nonatomic) IBOutlet UITextField *gameBlueName;

- (IBAction)goNext:(id)sender;
- (IBAction)goToGames:(id)sender;

@end

@implementation NewGame1ViewController

- (IBAction)goToGames:(id)sender{
    [self.navigationController dismissModalViewControllerAnimated:YES];
}
- (void)viewDidLoad
{
      [super viewDidLoad];
    self.view.backgroundColor = [UIColor ctfApplicationBackgroundLighterColor];
    
    _nameBackgroundView.backgroundColor = [UIColor ctfInputBackgroundAndDisabledButtonColor];
    _descriptionBackgroundView.backgroundColor = [UIColor ctfInputBackgroundAndDisabledButtonColor];
    
    _redBackgroundView.backgroundColor = [UIColor ctfInputBackgroundAndDisabledButtonColor];
    
    _blueBackgroundView.backgroundColor = [UIColor ctfInputBackgroundAndDisabledButtonColor];
    
    _backButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _backButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _nextButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _nextButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _topBar.backgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                   initWithTarget:self
                                   action:@selector(dismissKeyboard)];
    
    [self.view addGestureRecognizer:tap];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text
{
    if([text isEqualToString:@"\n"]){
        [textView resignFirstResponder];
    [self.gameRedName becomeFirstResponder];
    }
    return YES;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField == self.gameName) {
        [textField resignFirstResponder];
        [self.gameDescription becomeFirstResponder];
    }
        else if (textField == self.gameRedName) {
        [textField resignFirstResponder];
        [self.gameBlueName becomeFirstResponder];
    }
    else if (textField == self.gameBlueName) {
        [textField resignFirstResponder];
        [self goNext:nil];
    }

    return YES;
}

-(void)dismissKeyboard {
    [_gameName resignFirstResponder];
    [_gameDescription resignFirstResponder];
    [_gameBlueName resignFirstResponder];
    [_gameRedName resignFirstResponder];
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    CGRect textFieldRect =
    [self.view.window convertRect:textField.bounds fromView:textField];
    CGRect viewRect =
    [self.view.window convertRect:self.view.bounds fromView:self.view];
    CGFloat midline = textFieldRect.origin.y + 0.5 * textFieldRect.size.height;
    CGFloat numerator =
    midline - viewRect.origin.y
    - MINIMUM_SCROLL_FRACTION * viewRect.size.height;
    CGFloat denominator =
    (MAXIMUM_SCROLL_FRACTION - MINIMUM_SCROLL_FRACTION)
    * viewRect.size.height;
    CGFloat heightFraction = numerator / denominator;if (heightFraction < 0.0)
    {
        heightFraction = 0.0;
    }
    else if (heightFraction > 1.0)
    {
        heightFraction = 1.0;
    }
    _animatedDistance = floor(PORTRAIT_KEYBOARD_HEIGHT * heightFraction);
    CGRect viewFrame = self.view.frame;
    viewFrame.origin.y -= _animatedDistance;
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:KEYBOARD_ANIMATION_DURATION];
    
    [self.view setFrame:viewFrame];
    
    [UIView commitAnimations];
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    CGRect viewFrame = self.view.frame;
    viewFrame.origin.y += _animatedDistance;
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:KEYBOARD_ANIMATION_DURATION];
    
    [self.view setFrame:viewFrame];
    
    [UIView commitAnimations];
}

- (IBAction)goNext:(id)sender{
    if ([_gameName.text isEqualToString:@""] || [_gameDescription.text isEqualToString:@""] || [_gameRedName.text isEqualToString:@""] || [_gameBlueName.text isEqualToString:@""]) {
        [ShowInformation showError:@"Fill empty fields" inView:self.view];
    }
    else
    {
[self performSegueWithIdentifier:@"goToNewGame2" sender:self];
           }

}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    if ([[segue identifier] isEqualToString:@"goToNewGame2"]){
        CTFGame *myNewGame = [[CTFGame alloc] init];
        myNewGame.name = _gameName.text;
        myNewGame.gameDescription = _gameDescription.text;
        myNewGame.redTeamBaseName =_gameRedName.text;
        myNewGame.blueTeamBaseName = _gameBlueName.text;
        NewGame2ViewController *ng = [segue destinationViewController];
        ng.game = myNewGame;

    }
}

@end
