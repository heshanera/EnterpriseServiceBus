#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/299bfa85/Converter.o \
	${OBJECTDIR}/_ext/299bfa85/CppServiceDirectory.o


# C Compiler Flags
CFLAGS=-shared -m64

# CC Compiler Flags
CCFLAGS=-shared -m64
CXXFLAGS=-shared -m64

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk dist/libCppServices.so

dist/libCppServices.so: ${OBJECTFILES}
	${MKDIR} -p dist
	${LINK.cc} -o dist/libCppServices.so ${OBJECTFILES} ${LDLIBSOPTIONS} -shared -fPIC

${OBJECTDIR}/_ext/299bfa85/Converter.o: /home/heshan/Projects/NetBeansProjects/EnterpriseServiceBus/ServiceBus/src/services/CppServices/Converter.cpp
	${MKDIR} -p ${OBJECTDIR}/_ext/299bfa85
	${RM} "$@.d"
	$(COMPILE.cc) -g -I/home/usr/lib/jvm/java-8-openjdk/include -I/home/usr/lib/jvm/java-8-openjdk/include/linux -fPIC  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/299bfa85/Converter.o /home/heshan/Projects/NetBeansProjects/EnterpriseServiceBus/ServiceBus/src/services/CppServices/Converter.cpp

${OBJECTDIR}/_ext/299bfa85/CppServiceDirectory.o: /home/heshan/Projects/NetBeansProjects/EnterpriseServiceBus/ServiceBus/src/services/CppServices/CppServiceDirectory.cpp
	${MKDIR} -p ${OBJECTDIR}/_ext/299bfa85
	${RM} "$@.d"
	$(COMPILE.cc) -g -I/home/usr/lib/jvm/java-8-openjdk/include -I/home/usr/lib/jvm/java-8-openjdk/include/linux -fPIC  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/299bfa85/CppServiceDirectory.o /home/heshan/Projects/NetBeansProjects/EnterpriseServiceBus/ServiceBus/src/services/CppServices/CppServiceDirectory.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
