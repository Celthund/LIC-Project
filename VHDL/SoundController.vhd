----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:47:26 06/14/2020 
-- Design Name: 
-- Module Name:    SoundController - Structural 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SoundController is
	PORT (
		CLK : in STD_LOGIC;
		RST : in STD_LOGIC;
		Dval : in STD_LOGIC;
      Din : in  std_logic_vector(3 downto 0);
      sid : out std_logic_vector(1 downto 0);
		vol : out std_logic_vector(1 downto 0);
		Play : out STD_LOGIC;
      done : out STD_LOGIC
	);
end SoundController;

architecture Structural of SoundController is
	COMPONENT SoundCMDDecoder 
	Port ( S0 : in  STD_LOGIC;
			  S1 : in  STD_LOGIC;
			  Stop: out STD_LOGIC;
			  Enable : in STD_LOGIC;
           Play : out STD_LOGIC;
			  setSound : out STD_LOGIC;
			  setVolume : out STD_LOGIC);
	END COMPONENT;
	COMPONENT SoundCtrl 
	Port ( CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
			  Dval : in STD_LOGIC;
			  Enable : out STD_LOGIC;
			  done : out STD_LOGIC);
	END COMPONENT;
	COMPONENT Register_D
		GENERIC (
			WIDTH : POSITIVE 
		);
		PORT(
			CLK : IN std_logic;
			RST : IN std_logic;
			D : IN std_logic_vector(WIDTH-1 downto 0);          
			Q : OUT std_logic_vector(WIDTH-1 downto 0)
		);
	END COMPONENT;
	SIGNAL setPlay, clearPlay, setSound, setVolume, enable : STD_LOGIC;
begin
	SoundDecoder : SoundCMDDecoder 
	PORT MAP ( 
			S0 => Din(0),
			S1 => Din(1),
			Enable => enable,
			Stop => clearPlay,
			Play => setPlay,
			setSound => setSound,
			setVolume => setVolume
	);
	SoundASM : SoundCtrl 
	PORT MAP ( 
			RST => RST,
			CLK => CLK,
			Dval => Dval,
			Enable => enable,
			done => done
	);
	RegisterPlay : Register_D 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => setPlay,
			RST => clearPlay,
			D => "1",
			Q(0) => Play
	);
	RegisterVolume : Register_D 
	GENERIC MAP(
		WIDTH => 2
	)
	PORT MAP(
			CLK => setVolume,
			RST => RST,
			D => Din(3 downto 2),
			Q(0) => vol(0),
			Q(1) => vol(1)
	);
	RegisterSound : Register_D 
	GENERIC MAP(
		WIDTH => 2
	)
	PORT MAP(
			CLK => setSound,
			RST => RST,
			D => Din(3 downto 2),
			Q(0) => sid(0),
			Q(1) => sid(1)
	);
end Structural;

