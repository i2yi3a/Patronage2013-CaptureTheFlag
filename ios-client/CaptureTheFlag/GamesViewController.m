//
//  GamesViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 08.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "GamesViewController.h"

@interface GamesViewController ()

@property (weak, nonatomic) IBOutlet UIView *topBar;
@property (weak, nonatomic) IBOutlet FlatButton *CreateNewGameButton;
@property (weak, nonatomic) IBOutlet FlatButton *MyButton;
@property (weak, nonatomic) IBOutlet FlatButton *NearestButton;
@property (weak, nonatomic) IBOutlet FlatButton *EndedButton;
@property (weak, nonatomic) IBOutlet FlatButton *AllButton;
@property (weak, nonatomic) IBOutlet FlatButton *ProfileButton;
@property (weak, nonatomic) IBOutlet FlatButton *GameButton;
@property (weak, nonatomic) IBOutlet FlatButton *SettingsButton;

- (IBAction)My:(id)sender;
- (IBAction)Nearest:(id)sender;
- (IBAction)Ended:(id)sender;
- (IBAction)All:(id)sender;
- (IBAction)Profile:(id)sender;
- (IBAction)Game:(id)sender;
- (IBAction)Settings:(id)sender;


@end

@implementation GamesViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
     self.view.backgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _CreateNewGameButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    
    _MyButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _MyButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _NearestButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _NearestButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _EndedButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _EndedButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];

    _AllButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _AllButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _ProfileButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _ProfileButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];

    _GameButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _GameButton.buttonHighlightedBackgroundColor = [UIColor ctfTabColor];
    
    _SettingsButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _SettingsButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];


	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)My:(id)sender{
    _MyButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _MyButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _NearestButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _NearestButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _EndedButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _EndedButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _AllButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _AllButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
}

- (IBAction)Nearest:(id)sender{
    _MyButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _MyButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _NearestButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _NearestButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _EndedButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _EndedButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _AllButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _AllButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
}

- (IBAction)Ended:(id)sender{
    _MyButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _MyButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _NearestButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _NearestButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _EndedButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _EndedButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _AllButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _AllButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
}

- (IBAction)All:(id)sender{
    _MyButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _MyButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _NearestButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _NearestButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _EndedButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _EndedButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
    
    _AllButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelTurquoiseColor];
    _AllButton.buttonHighlightedBackgroundColor = [UIColor ctfPressedButtonAndWindowBackgroundColor];
}

- (IBAction)Profile:(id)sender{
    _ProfileButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _ProfileButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _GameButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _GameButton.buttonHighlightedBackgroundColor = [UIColor ctfTabColor];
    
    _SettingsButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _SettingsButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
}

- (IBAction)Game:(id)sender{
    _ProfileButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _ProfileButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _GameButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _GameButton.buttonHighlightedBackgroundColor = [UIColor ctfTabColor];
    
    _SettingsButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _SettingsButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
}

- (IBAction)Settings:(id)sender{
    _ProfileButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _ProfileButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _GameButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _GameButton.buttonHighlightedBackgroundColor = [UIColor ctfTabColor];
    
    _SettingsButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _SettingsButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
}



- (void)viewDidUnload {
    [self setTopBar:nil];
    [self setMyButton:nil];
    [self setNearestButton:nil];
    [self setAllButton:nil];
    [self setEndedButton:nil];
    [self setGameButton:nil];
    [self setProfileButton:nil];
    [self setSettingsButton:nil];
    [super viewDidUnload];
}


@end
