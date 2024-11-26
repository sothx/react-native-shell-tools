import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  execAsyncCommand(shellCommon: string): Promise<string>;
  execCommand(shellCommon: string): string;
}

export default TurboModuleRegistry.getEnforcing<Spec>('ReactNativeShellTools');
