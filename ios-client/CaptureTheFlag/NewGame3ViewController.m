//
//  NewGame3ViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 21.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NewGame3ViewController.h"
#import "NewGame4ViewController.h"
#import "CTFGame.h"

@interface NewGame3ViewController ()

@property (weak, nonatomic) IBOutlet FlatButton *backButton;
@property (weak, nonatomic) IBOutlet FlatButton *nextButton;
@property (weak, nonatomic) IBOutlet UIView *topBar;
@property (weak, nonatomic) IBOutlet UIDatePicker *gameDuration;

- (IBAction)goToCreatingNewGame2:(id)sender;
- (IBAction)goNext:(id)sender;
@end

@implementation NewGame3ViewController

- (IBAction)goToCreatingNewGame2:(id)sender{
    [self.navigationController popViewControllerAnimated:YES];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor ctfApplicationBackgroundLighterColor];
    _backButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _backButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _nextButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _nextButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _topBar.backgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];

	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)goNext:(id)sender{
    [self performSegueWithIdentifier:@"goToNewGame4" sender:self];
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    if ([[segue identifier] isEqualToString:@"goToNewGame4"]){
        NewGame4ViewController *ng = [segue destinationViewController];
        NSNumber *durarionInMs = [NSNumber numberWithDouble:_gameDuration.countDownDuration];
        _game.duration = durarionInMs;
        ng.game = _game;
        
    }
}

@end
