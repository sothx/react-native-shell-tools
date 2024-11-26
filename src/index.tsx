const ReactNativeShellTools = require('./NativeReactNativeShellTools').default;

export function multiply(a: number, b: number): number {
  return ReactNativeShellTools.multiply(a, b);
}

export function execCommand(shellCommon: string): String {
  return ReactNativeShellTools.execCommand(shellCommon);
}

export function execAsyncCommand(shellCommon: string): Promise<string> {
  return ReactNativeShellTools.execAsyncCommand(shellCommon);
}
