import {Alert, NativeEventEmitter, NativeModules} from 'react-native';

const NativeModuleStart = NativeModules.NativeModuleStart;

export const incrementValue = (value: number): Promise<number> => {
    return NativeModuleStart.incrementValue(value);
};

export const incrementValueSync = (value: number): number => {
    console.info(NativeModuleStart.incrementValueSync(value))
    return NativeModuleStart.incrementValueSync(value);
};

export const nativeModuleView = (elementRef: number) => {
    console.info('elementRef', elementRef);
    NativeModuleStart.nativeModuleView(elementRef);
};

const nativeEventEmitter = new NativeEventEmitter(NativeModuleStart);
nativeEventEmitter.addListener('nativeModuleStartEvent', (value: string) => {
    Alert.alert(value);
});
