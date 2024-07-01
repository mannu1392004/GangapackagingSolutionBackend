package com.example.GangaPackage.config

import com.twilio.Twilio
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TwilioConfig {

   @Bean
   fun twilioInitializer(): TwilioInitializer{
       Twilio.init("AC396e1366bcfb397c0beddd5c4ed98e53","e1e458616e860e08617096b5be91f49c")

       return TwilioInitializer()
   }

}

class TwilioInitializer