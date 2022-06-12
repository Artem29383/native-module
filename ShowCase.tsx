import React, {useState} from 'react';
import {Alert, Platform, View, Text, findNodeHandle} from 'react-native';
import {useSafeAreaInsets} from 'react-native-safe-area-context';
import Carousel from 'react-native-snap-carousel';
import styled from 'styled-components/native';

import AuthContainer from '../../../../components/container/AuthContainerNew';
import TzButton from '../../../../components/controls/button/TzButton';
import WelcomeCarousel from './components/WelcomeCarousel';
import {AuthType} from '@core/settings/configuration/app/AppConfiguration';
import {t} from '@src/common/utils/locale/i18n';
import {ThemeProps} from '@src/common/utils/theme';
import {navigationGoTo, navigatorAuth} from '@src/tezis/navigation/navigation';
import {TestAttributeContextProvider} from '@src/tezis/utils/TestAttributeUtils';
import {Slides} from './constants';
import {useMaintenanceMessage} from '../../useMaintenanceMessage';
import {incrementValue, incrementValueSync, nativeModuleView} from '@src/common/native/NativeModuleStart';

const ButtonsContainer = styled.View`
    display: flex;
    flex-direction: column;
    margin-bottom: ${(props: {bottomIndent: number} & ThemeProps) => props.bottomIndent}px;
    margin-horizontal: ${({theme}: ThemeProps) => theme.indent.regular * 3};
    margin-top: auto;
`;

const Button = styled(TzButton)`
    border: none;
    margin-bottom: ${({theme}) => theme.indent.regular * 2};
`;

const Showcase = () => {
    const elementRef = React.useRef(null);
    const [currentCarouselIndex, setCarouselIndex] = useState(0);
    useMaintenanceMessage();

    const carouselRef = React.useRef<Carousel<string>>();

    const indents = useSafeAreaInsets();
    const bottomIndent = Platform.OS === 'ios' ? indents.bottom : 0;

    const onContinue = () => {
        goToAuthorization('newton');
    };

    const onNextPress = () => {
        if (currentCarouselIndex < Slides.length - 1) {
            setCarouselIndex(currentCarouselIndex + 1);
            carouselRef.current?.snapToItem(currentCarouselIndex + 1);
        } else {
            onContinue();
        }
    };

    return (
        <AuthContainer>
            <WelcomeCarousel
                onIndexChange={(index) => setCarouselIndex(index)}
                currentIndex={currentCarouselIndex}
                carouselRef={carouselRef}
            />
            <ButtonsContainer bottomIndent={bottomIndent}>
                <View
                    ref={(ref) => {
                        elementRef.current = findNodeHandle(ref);
                    }}
                    style={{
                        zIndex: 100,
                    }}>
                    <Text
                        style={{
                            color: 'black',
                        }}>
                        UI DRAGGABLE
                    </Text>
                </View>
                <TestAttributeContextProvider value={'showcase_forward'}>
                    <Button onPress={onNextPress} title={t('AUTHORIZATION.CONTROLS.PROCEED')} primary />
                </TestAttributeContextProvider>
                <TestAttributeContextProvider value={'showcase_skip'}>
                    <Button
                        onPress={() => {
                            nativeModuleView(elementRef.current);
                            // Alert.alert(incrementValueSync(14).toString(10));
                            // incrementValue(14)
                            //     .then(value => Alert.alert(value.toString(10)))
                        }}
                        title={t('AUTHORIZATION.CONTROLS.SKIP')}
                    />
                </TestAttributeContextProvider>
            </ButtonsContainer>
        </AuthContainer>
    );
};

const goToAuthorization = (authType: AuthType) => {
    navigationGoTo(authType === 'newton' ? navigatorAuth.AuthPhone : navigatorAuth.AuthSelector);
};

export default React.memo(Showcase);
