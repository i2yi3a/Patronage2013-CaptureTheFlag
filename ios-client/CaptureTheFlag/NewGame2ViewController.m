//
//  NewGame2ViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 21.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NewGame2ViewController.h"
#import "CTFGame.h"

@interface NewGame2ViewController ()


@property (weak, nonatomic) IBOutlet FlatButton *backButton;
@property (weak, nonatomic) IBOutlet FlatButton *nextButton;
@property (weak, nonatomic) IBOutlet UIView *topBar;

- (IBAction)goToCreatingNewGame1:(id)sender;
@end

@implementation NewGame2ViewController

- (IBAction)goToCreatingNewGame1:(id)sender{
   [self.navigationController popViewControllerAnimated:YES];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    _gameStart.minimumDate = [NSDate date];
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

@end
