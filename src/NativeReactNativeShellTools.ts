import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  execAsyncCommand(shellCommand: string): Promise<string>;
  execCommand(shellCommand: string): string;
}

export default TurboModuleRegistry.getEnforcing<Spec>('ReactNativeShellTools');
