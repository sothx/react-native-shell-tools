const ReactNativeShellTools = require('./NativeReactNativeShellTools').default;

export function multiply(a: number, b: number): number {
  return ReactNativeShellTools.multiply(a, b);
}

export function execCommand(shellCommand: string): String {
  return ReactNativeShellTools.execCommand(shellCommand);
}

export function execAsyncCommand(shellCommand: string): Promise<string> {
  return ReactNativeShellTools.execAsyncCommand(shellCommand);
}
