//
//  MainViewController.h
//  CaptureTheFlag
//
//  Created by Konrad Gnoinski on 12.04.2013.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LoginViewController : UIViewController{
    IBOutlet UISegmentedControl *control;
}
- (IBAction)login:(id)sender;
- (IBAction)switchcontrol:(id)sender;
@end