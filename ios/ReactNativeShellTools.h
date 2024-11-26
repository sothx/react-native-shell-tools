
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNReactNativeShellToolsSpec.h"

@interface ReactNativeShellTools : NSObject <NativeReactNativeShellToolsSpec>
#else
#import <React/RCTBridgeModule.h>

@interface ReactNativeShellTools : NSObject <RCTBridgeModule>
#endif

@end
