//
//  ViewController.h
//  CaptureTheFlag
//
//  Created by Sebastian Jędruszkiewicz on 4/4/13.
//  Copyright (c) 2013 BLStream. All rights reserved.
//

 #import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController{
    IBOutlet UISegmentedControl *control;
}
- (IBAction)reginster:(id)sender;
- (IBAction)switchcontrol:(id)sender;
@end
