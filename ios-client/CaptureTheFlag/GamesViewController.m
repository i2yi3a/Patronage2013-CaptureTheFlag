//
//  GamesViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 08.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "GamesViewController.h"
#import "GamesViewCell.h"

@interface GamesViewController ()

@property (weak, nonatomic) IBOutlet UITableView *table;

@property (weak, nonatomic) IBOutlet UIView *topBar;
@property (weak, nonatomic) IBOutlet UIView *mainView;
@property (weak, nonatomic) IBOutlet UIView *SettingsView;
@property (weak, nonatomic) IBOutlet FlatButton *CreateNewGameButton;
@property (weak, nonatomic) IBOutlet FlatButton *MyButton;
@property (weak, nonatomic) IBOutlet FlatButton *NearestButton;
@property (weak, nonatomic) IBOutlet FlatButton *EndedButton;
@property (weak, nonatomic) IBOutlet FlatButton *AllButton;
@property (weak, nonatomic) IBOutlet FlatButton *ProfileButton;
@property (weak, nonatomic) IBOutlet FlatButton *GameButton;
@property (weak, nonatomic) IBOutlet FlatButton *SettingsButton;
@property (weak, nonatomic) IBOutlet FlatButton *LogoutButton;

- (IBAction)My:(id)sender;
- (IBAction)Nearest:(id)sender;
- (IBAction)Ended:(id)sender;
- (IBAction)All:(id)sender;
- (IBAction)Profile:(id)sender;
- (IBAction)Game:(id)sender;
- (IBAction)Settings:(id)sender;
- (IBAction)Logout:(id)sender;


@end

@implementation GamesViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.GamesViewCellTitleLabels= [[NSArray alloc]
                               initWithObjects:@"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", @"Nazwa gry capture the flag", nil];
    self.GamesViewCellTimeLabels= [[NSArray alloc]
                               initWithObjects:@"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", @"30 MIN. 14-06-2013", nil];
    self.GamesViewCellLocationLabels= [[NSArray alloc]
                               initWithObjects:@"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", @"1,4 KM - SZCZECIN", nil];
    self.GamesViewCellRoundImages = [[NSArray alloc]
                                initWithObjects:@"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", @"BLstream.png", nil];
    self.view.backgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    self.table.backgroundColor = [UIColor ctfApplicationBackgroundLighterColor];
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
    
    _SettingsView.alpha=0;


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
    _mainView.alpha=1;
    _SettingsView.alpha=0;
}

- (IBAction)Settings:(id)sender{
    _ProfileButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _ProfileButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    
    _GameButton.buttonBackgroundColor = [UIColor ctfTabColor];
    _GameButton.buttonHighlightedBackgroundColor = [UIColor ctfTabColor];
    
    _SettingsButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _SettingsButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _mainView.alpha=0;
    _SettingsView.alpha=1;
    
    _LogoutButton.buttonBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];
    _LogoutButton.buttonHighlightedBackgroundColor = [UIColor ctfNormalButtonAndLabelCarrotColor];

    
}

- (IBAction)Logout:(id)sender{
    [[NetworkEngine getInstance] logout:^(NSObject *object) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.GamesViewCellTitleLabels count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"GamesCell";
    
    GamesViewCell *cell = [tableView
                           dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[GamesViewCell alloc]
                initWithStyle:UITableViewCellStyleDefault
                reuseIdentifier:CellIdentifier];
    }
    // Configure the cell...
    cell.GamesViewCellTitleLabel.text = [self.GamesViewCellTitleLabels
                                             objectAtIndex: [indexPath row]];
    
    cell.GamesViewCellTimeLabel.text = [self.GamesViewCellTimeLabels
                                         objectAtIndex: [indexPath row]];
    
    cell.GamesViewCellLocationLabel.text = [self.GamesViewCellLocationLabels
                                         objectAtIndex: [indexPath row]];
    UIImage *menuImage = [UIImage imageNamed:
                          [self.GamesViewCellRoundImages objectAtIndex: [indexPath row]]];
    
    cell.GamesViewCellRoundImage.image = menuImage;
    
    return cell;
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
    [self setCreateNewGameButton:nil];
    [self setLogoutButton:nil];
    [super viewDidUnload];
}


@end
