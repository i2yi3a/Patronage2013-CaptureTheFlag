//
//  NewGame4ViewController.m
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 21.06.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import "NewGame4ViewController.h"
#import "CTFGame.h"

@interface NewGame4ViewController ()

@property (weak, nonatomic) IBOutlet FlatButton *backButton;
@property (weak, nonatomic) IBOutlet FlatButton *nextButton;
@property (weak, nonatomic) IBOutlet UIView *topBar;
@property (strong, nonatomic) NSArray *oneColumnList;
@property (strong, nonatomic) NSArray *secondColumnList;

- (IBAction)goToCreatingNewGame3:(id)sender;
@end

@implementation NewGame4ViewController

- (IBAction)goToCreatingNewGame3:(id)sender{
[self.navigationController popViewControllerAnimated:YES];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    _gamePicker.showsSelectionIndicator = TRUE;
    self.oneColumnList=[[NSArray alloc] initWithObjects:@"2",@"3",@"4",@"5",@"6",@"7",@"8",@"9",@"10",@"11",@"12", nil];
    self.secondColumnList=[[NSArray alloc] initWithObjects:@"5", @"10",@"15",@"20",@"25",@"30", @"40",@"50", nil];
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

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    if (component == 0) {
        return [_oneColumnList count];
    }
    
    return [_secondColumnList count];
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 2;
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row
            forComponent:(NSInteger)component
{
    if (component == 0) {
        return [_oneColumnList objectAtIndex:row];
        
    }
    
    return [_secondColumnList objectAtIndex:row];
    
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (component == 0) {
        return;
    }
    
    
}


@end
