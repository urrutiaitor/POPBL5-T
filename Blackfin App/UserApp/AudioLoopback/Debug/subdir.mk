################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../AudioLoopback.c \
../codificador.c \
../decodificador.c \
../demodulador.c \
../filter_fr32.c \
../modulador.c 

LDF_SRCS += \
../app.ldf 

SRC_OBJS += \
./AudioLoopback.doj \
./codificador.doj \
./decodificador.doj \
./demodulador.doj \
./filter_fr32.doj \
./modulador.doj 

C_DEPS += \
./AudioLoopback.d \
./codificador.d \
./decodificador.d \
./demodulador.d \
./filter_fr32.d \
./modulador.d 


# Each subdirectory must supply rules for building sources it contributes
%.doj: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: CrossCore Blackfin C/C++ Compiler'
	ccblkfn.exe -c -file-attr ProjectName="AudioLoopback" -proc ADSP-BF537 -flags-compiler --no_wrap_diagnostics -si-revision any -g -D_DEBUG -DCORE0 -I"\\vmware-host\Shared Folders\aitorurrutia en mi Mac\GitHub\POPBL5-T\Blackfin App\UserApp\AudioLoopback\system" -I"C:/Analog Devices/ADSP-BF537_EZKIT-Rel1.0.0/BF537_EZ-KIT_Lite/Blackfin/include" -I"C:\Analog Devices\CrossCore Embedded Studio 1.0.2\Blackfin\lib\src\libdsp" -structs-do-not-overlap -no-const-strings -no-multiline -warn-protos -double-size-32 -decls-strong -cplbs -sdram -gnu-style-dependencies -MD -Mo "AudioLoopback.d" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


