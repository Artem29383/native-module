//
//  NativeModuleStartModule.m
//  mobile
//
//  Created by Артем Аверьянов on 12.06.2022.
//  Copyright © 2022 Newton Technology. All rights reserved.
//

#import "React/RCTBridgeModule.h"
#import "React/RCTViewManager.h"

@interface RCT_EXTERN_MODULE(NativeModuleStart, NSObject)

RCT_EXTERN_METHOD(
  incrementValue:(NSInteger)value
  resolve:(RCTPromiseResolveBlock)resolve
  rejecter:(RCTPromiseRejectBlock)reject
)

RCT_EXTERN__BLOCKING_SYNCHRONOUS_METHOD(incrementValueSync:(NSInteger)value)

RCT_EXTERN_METHOD(nativeModuleView:(NSInteger)elementRef)

@end
