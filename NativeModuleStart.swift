//
//  NativeModuleStartModule.swift
//  mobile
//
//  Created by Артем Аверьянов on 12.06.2022.
//  Copyright © 2022 Newton Technology. All rights reserved.
//

import Foundation
import UIKit

@objc(NativeModuleStart)
class NativeModuleStart: RCTEventEmitter {
    
    private var rootView: UIView? {
        get {
            return UIApplication.shared.delegate?.window??.rootViewController?.view
        }
    }
    
    override public static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override func supportedEvents() -> [String]! {
        return ["nativeModuleStartEvent"];
    }
    
    @objc(incrementValue:resolve:rejecter:)
    public func incrementValue(value: Int, resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
        resolve("\(value + 11)")
    }
    
    @objc(incrementValueSync:)
    public func incrementValueSync(value: Int) -> String {
        DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
            self.sendEvent(withName: "nativeModuleStartEvent", body: "Data from native IOS")
        }
        return "\(value + 11)"
    }
    
    @objc(nativeModuleView:)
    public func nativeModuleView(elementRef: Int) {
        DispatchQueue.main.async(execute: {
            guard let elementView = AppManager.instance.bridge?.uiManager.view(forReactTag: NSNumber(value: elementRef)) else {
                return
            }
            
            let pan = UIPanGestureRecognizer(target: self, action: #selector(self.handlePan(_:)))
            
            elementView.addGestureRecognizer(pan)
        })
    }
    
    @objc func handlePan(_ gestureRecognizer: UIPanGestureRecognizer) {
        guard let rootView = self.rootView else {
            return
        }
        
        if (gestureRecognizer.state == .began || gestureRecognizer.state == .changed) {
            let translation = gestureRecognizer.translation(in: rootView)
            
            gestureRecognizer.view!.center = CGPoint(x: gestureRecognizer.view!.center.x + translation.x, y: gestureRecognizer.view!.center.y + translation.y)
            
            gestureRecognizer.setTranslation(CGPoint.zero, in: rootView)
        }
    }
}
